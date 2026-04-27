function openModal() {
	const modal = document.getElementById('modify-check-modal');
	
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

async function checkModify() {
	console.log('asdf');
	
	oldData.nyuusyaDate = oldData.nyuusyaDate ? oldData.nyuusyaDate.substring(0, 10) : oldData.nyuusyaDate;
	oldData.taisyaDate = oldData.taisyaDate ? oldData.taisyaDate.substring(0, 10) : oldData.taisyaDate;
	
	console.log(oldData);
	
	const syainId = document.getElementById('syainId').value;
	const firstNameKanji = document.getElementById('firstNameKanji').value;
	const lastNameKanji = document.getElementById('lastNameKanji').value;
	const firstNameKana = document.getElementById('firstNameKana').value;
	const lastNameKana = document.getElementById('lastNameKana').value;
	const firstNameEigo = document.getElementById('firstNameEigo').value;
	const lastNameEigo = document.getElementById('lastNameEigo').value;
	const seibetu = document.querySelector('input[name="seibetu"]:checked').value;
	const syozokuKaisya = document.getElementById('syozokuKaisya').value;
	let nyuusyaDate = document.getElementById('nyuusyaDate').value;
	let taisyaDate = document.getElementById('taisyaDate').value;
	const syokugyoKind = document.getElementById('syokugyoKind').value;
	
	taisyaDate = taisyaDate === '' ? null : taisyaDate;
	
	if (oldData.firstNameKanji == firstNameKanji
		&& oldData.lastNameKanji == lastNameKanji
		&& oldData.firstNameKana == firstNameKana
		&& oldData.lastNameKana == lastNameKana
		&& oldData.firstNameEigo == firstNameEigo
		&& oldData.lastNameEigo == lastNameEigo
		&& oldData.seibetu == seibetu
		&& oldData.syozokuKaisya == syozokuKaisya
		&& oldData.nyuusyaDate == nyuusyaDate
		&& oldData.taisyaDate == taisyaDate
		&& oldData.syokugyoKind == syokugyoKind) {
			console.log('바뀐게업다');
			openModal();
		}
		
	const newData = {
		syainId: syainId,
		firstNameKanji: firstNameKanji,
		lastNameKanji: lastNameKanji,
		firstNameKana: firstNameKana,
		lastNameKana: lastNameKana,
		firstNameEigo: firstNameEigo,
		lastNameEigo: lastNameEigo,
		seibetu: seibetu,
		syozokuKaisya: syozokuKaisya,
		nyuusyaDate: nyuusyaDate,
		taisyaDate: taisyaDate,
		syokugyoKind: syokugyoKind
	}
	
	modifyUser(newData);
}

async function modifyUser(data) {
	const params = new URLSearchParams({
		syainId: data.syainId,
		firstNameKanji: data.firstNameKanji,
		lastNameKanji: data.lastNameKanji,
		firstNameKana: data.firstNameKana,
		lastNameKana: data.lastNameKana,
		firstNameEigo: data.firstNameEigo,
		lastNameEigo: data.lastNameEigo,
		seibetu: data.seibetu,
		syozokuKaisya: data.syozokuKaisya,
		syokugyoKind: data.syokugyoKind
	});
	
	if (data.nyuusyaDate != null) {
		params.append('nyuusyaDate', data.nyuusyaDate);
	}
	
	if (data.taisyaDate != null) {
		params.append('taisyaDate', data.taisyaDate);
	}
	
	try {
		const response = await fetch(`/api/modify-user?${params.toString()}`); 
		const data = await response.json();
		
		console.log(data);
		
		location.href = '/done';
	}
	catch (error) {
		console.error("更新中エラー", error);
	}
}

/*(async () => {
	try {
		await Promise.all([loadCompanyData(), loadJobTypeData()]);
	}
	catch(err) {
		console.log("初期化エラー");
	}
})();
*/