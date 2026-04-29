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

	const fields = [
		{
			id: 'syainId',
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
			id: 'syozokuKaisya',
			name: '所属会社',
			required: true,
			pattern: null,
			max: null
		},
		{
			id: 'nyuusyaDate',
			name: '入社日',
			required: true,
			pattern: null,
			max: null
		},
		{
			id: 'taisyaDate',
			name: '退社日',
			required: false,
			pattern: null,
			max: null
		},
		{
			id: 'syokugyoKind',
			name: '職業種類',
			required: true,
			pattern: null,
			max: null
		}
	]

	const newData = {};
	
	const seibetuEl = document.querySelector('input[name="seibetu"]:checked');
	
	if (!seibetuEl) {
	    alert('性別を選択してください');
	    return;
	}
	
	newData['seibetu'] = seibetuEl.value;

	for (const field of fields) {
		const input = document.getElementById(field.id);
		const value = input.value.trim();

		if (field.required && value === '') {
			alert(`${field.name}を入力してください。`);
			return;
		}

		if (value !== '' && field.pattern && !field.pattern.test(value)) {
			alert(`${field.name}は${field.patternName}で入力してください。\n${value}`);
			return;
		}

		if (field.max && value.length > field.max) {
			alert(`${field.name}は${field.max}字以下で入力してください。`);
			return;
		}

		newData[field.id] = value || null;
	}
	
	const isNotChanged = Object.keys(newData).every(key => {
		const oldVal = oldData[key] == null ? "" : String(oldData[key]);
		const newVal = newData[key] == null ? "" : String(newData[key]);
		return oldVal === newVal;
	})
	
	if (isNotChanged) {
		console.log('変更なし');
		openModal();
		return;
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