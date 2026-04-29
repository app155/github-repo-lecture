async function loadCompanyData() {
    try {
        const response = await fetch('/api/get-company'); 
        const data = await response.json();

        const selectElement = document.getElementById('company');
		
		companyList = data;
		
		console.log(data);

        data.forEach(item => {
            const option = document.createElement('option');
            option.value = item.category2;
            option.text = item.value1;
            selectElement.appendChild(option);
        });
    } 
	catch (error) {
        console.error('会社データロード失敗:', error);
    }
}

async function loadJobTypeData() {
    try {
        const response = await fetch('/api/get-job-type'); 
        const data = await response.json();

        const selectElement = document.getElementById('job-type');
		
		jobTypeList = data;
		
		console.log(data);

        data.forEach(item => {
            const option = document.createElement('option');
            option.value = item.category3;
            option.text = item.value1;
            selectElement.appendChild(option);
			
			if (item.value1 == 'ITエンジニア') {
				option.selected = true;
			}
        });
    } 
	catch (error) {
        console.error('会社データロード失敗:', error);
    }
}

async function fetchDataBySelection(e) {
	const active = document.getElementById('active').checked;
	const inactive = document.getElementById('inactive').checked;
	
	if (!active && !inactive) {
		const checkModal = document.getElementById('checkbox-modal');
		checkModal.showModal();
		return;
	}
	
	
	if (e) e.preventDefault();
    const company = document.getElementById('company').value;
    const employeeName = document.getElementById('employee-name').value;
    const jobType = document.getElementById('job-type').value;

    const params = new URLSearchParams({
        company: company,
        name: employeeName,
        jobType: jobType,
        active: active,
        inactive: inactive
    });

    try {
        const response = await fetch(`/api/get-employees?${params.toString()}`);
        const data = await response.json();

        console.log("検索データ:", data);
		
		reloadUserDataList(data);
    } catch (error) {
        console.error("エラー発生:", error);
    }
}

async function reloadUserDataList(data) {
	const datanum = Object.keys(data).length;
	const numtext = document.getElementById('data-num');
	numtext.innerHTML = `件数：${datanum}件`;
	const tbody = document.querySelector('.employee-table tbody');
	tbody.innerHTML = '';
	
	data.forEach(item => {
		const row = document.createElement('tr');
		
		row.innerHTML = `
		            <td>${companyList[item.syozokuKaisya - 1].value1 || '-'}</td>
		            <td>${item.firstNameKanji} ${item.lastNameKanji}</td>
		            <td>${item.seibetu === 1 ? '男性' : '女性'}</td>
		            <td>${jobTypeList[item.syokugyoKind - 1].value1 || '-'}</td>
		            <td>${item.nyuusyaDate || '-'}</td>
		            <td>${item.taisyaDate || '-'}</td>
		            <td><a href="/download/resume/${item.syainId}">職무経歴書</a></td>
		            <td>
						<a href="/modify?syainId=${item.syainId}">編集</a><br>
						<a href="#" onclick="openDeleteModal('${item.firstNameKanji}${item.lastNameKanji}', ${item.syainId})">削除</a>
					</td>
		        `;
				
		tbody.appendChild(row);
	});
}

function openDeleteModal(name, id) {
	const modal = document.getElementById('delete-modal');
	const modalContent = document.querySelector('#delete-modal .modal-content');
	const btn = document.getElementById('delete-btn');
	
	btn.onclick = function() {
		deleteButtonClick(id);
		modal.close();
	}
	
	modalContent.innerHTML = name + 'を削除してもよろしいですか？';
	
	modal.showModal();
}

async function deleteButtonClick(id) {
	try {
		const response = await fetch(`/api/delete-user?syainId=${id}`);
		const data = await response.json();
		
		fetchDataBySelection();
	}
	catch (error) {
		console.error("でーた削除エラー: ", error);
	}
}

let companyList = [];
let jobTypeList = [];

(async () => {
	try {
		await Promise.all([loadCompanyData(), loadJobTypeData()]);
		fetchDataBySelection();
	}
	catch(err) {
		console.log("初期化エラー");
	}
})();

document.getElementById('search-btn').addEventListener('click', fetchDataBySelection);