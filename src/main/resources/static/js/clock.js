function updateTime() {
    const now = new Date();
    
	const year = String(now.getFullYear());
	const month = String(now.getMonth() + 1).padStart(2, '0');
	const date = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    
    const timeString = "登録日時 : " + `${year}年${month}月${date}日　${hours}:${minutes}:${seconds}`;
    
    document.getElementById('current-time').innerText = timeString;
}

updateTime();
setInterval(updateTime, 1000);