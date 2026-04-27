function updateTime() {
    const now = new Date();
    
    // 시, 분, 초를 가져와서 2자리 숫자로 맞춤 (예: 9시 -> 09시)
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    
    const timeString = "登録日時 : " + `${hours}:${minutes}:${seconds}`;
    
    // 화면의 글자만 교체 (페이지 갱신 X)
    document.getElementById('current-time').innerText = timeString;
}

updateTime();
setInterval(updateTime, 1000);