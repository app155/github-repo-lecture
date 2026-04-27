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
	
	const formData = new FormData(event.target);
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
			location.href = '/error';
		}
	}
	catch(error) {
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
