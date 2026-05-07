async function loadCompanyData() {
	try {
		const response = await fetch('/api/get-company');
		const data = await response.json();

		const selectElement = document.getElementById('syozokuKaisya');

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

		const selectElement = document.getElementById('syokugyoKind');

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

async function loadOSData() {
	try {
		const response = await fetch('/api/get-os');
		const data = await response.json();

		console.log(data);

		const tbody = document.querySelector('.skill-table tbody');
		tbody.innerHTML = '';

		for (let i = 0; i < data.length; i += 5) {
			const chunk = data.slice(i, i + 5);
			const row = document.createElement('tr');

			if (i === 0) {
				const th = document.createElement('th');
				th.textContent = 'OS';
				th.rowSpan = Math.ceil(data.length / 5);
				row.appendChild(th);
			}

			for (let j = 0; j < 5; j++) {
				const tdName = document.createElement('td');
				const tdInput = document.createElement('td');
				tdName.className = 'skill-name';
				tdInput.className = 'skill-input';

				if (chunk[j]) {
					tdName.textContent = chunk[j];
					tdInput.innerHTML = `<select>
						<option value=""></option>
						<option value="◎">◎</option>
						<option value="○">○</option>
						<option value="△">△</option>
					</select>`;
				}

				row.appendChild(tdName);
				row.appendChild(tdInput);
			}

			tbody.appendChild(row);
		}
	}
	catch (error) {
		console.error('OSデータロード失敗:', error);
	}
}

async function registerUser(e) {
	e.preventDefault();

	const fields = [
		{
			id: 'employeecode',
			name: '社員コード',
			required: true,
			pattern: /^[0-9a-zA-Z]*$/,
			patternName: '英文または数字',
			max: 10
		},
		{
			id: 'firstNameKanji',
			name: '社員名（漢字）性',
			required: true,
			pattern: /^[^\x01-\x7E\xA1-\xDF]*$/,
			patternName: '全角',
			max: 15
		},
		{
			id: 'lastNameKanji',
			name: '社員名（漢字）名',
			required: true,
			pattern: /^[^\x01-\x7E\xA1-\xDF]*$/,
			patternName: '全角',
			max: 15
		},
		{
			id: 'firstNameKana',
			name: '社員名（カタカナ）性',
			required: true,
			pattern: /^[^\x01-\x7E\xA1-\xDF]*$/,
			patternName: '全角',
			max: 15
		},
		{
			id: 'lastNameKana',
			name: '社員名（カタカナ）名',
			required: true,
			pattern: /^[^\x01-\x7E\xA1-\xDF]*$/,
			patternName: '全角',
			max: 15
		},
		{
			id: 'firstNameEigo',
			name: '社員名（英語）firstname',
			required: true,
			pattern: /^[a-zA-Z]*$/,
			patternName: '英文',
			max: 30
		},
		{
			id: 'lastNameEigo',
			name: '社員名（英語）lastname',
			required: true,
			pattern: /^[a-zA-Z]*$/,
			patternName: '英文',
			max: 30
		},
		{
			id: 'bank_code',
			name: '金融機関コード',
			required: true,
			pattern: /^[0-9a-zA-Z]*$/,
			patternName: '数字',
			max: 10
		},
		{
			id: 'branch_code',
			name: '支店名コード',
			required: true,
			pattern: /^[0-9a-zA-Z]*$/,
			patternName: '数字',
			max: 10
		},
		{
			id: 'account_num',
			name: '口座番号',
			required: true,
			pattern: /^[0-9]*$/,
			patternName: '数字',
			max: 10
		},
		{
			id: 'owner_name',
			name: '名義人',
			required: true,
			pattern: null,
			patternName: '',
			max: 50
		}
	]

	for (let field of fields) {
		const input = document.getElementById(field.id);
		const value = input.value;

		if (field.required && value === '') {
			alert(`${field.name}を入力してください。`);
			return;
		}

		if (value !== '' && field.pattern && !field.pattern.test(value)) {
			alert(`${field.name}は${field.patternName}で入力してください。\n${value}`);
			return;
		}

		if (value.length > field.max) {
			alert(`${field.name}は${field.max}字以下で入力してください。`);
			return;
		}
	}

	const formData = new FormData(e.target);
	const data = Object.fromEntries(formData.entries());

	try {
		const response = await fetch('/api/add-user', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(data)
		});


		if (response.ok) {
			location.href = '/done';
		}
		else {
			location.href = '/error-page';
		}
	}
	catch (error) {
		console.error(error);
		alert(error);
	}
}

(async () => {
	try {
		await Promise.all([loadCompanyData(), loadJobTypeData(), loadOSData()]);
	}
	catch (err) {
		console.log("初期化エラー");
	}
})();
