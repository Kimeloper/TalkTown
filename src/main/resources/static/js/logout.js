const logoutButton = document.getElementById('logout_btn');

if(logoutButton){
    logoutButton.addEventListener('click', () => {
        fetch(`/api/logout`,
            {method: 'POST'})
            .then(response => {
                if(response.ok){
                    alert('정상적으로 로그아웃되었습니다.');
                    window.location.replace('/main');
                }else{
                    alert('로그아웃을 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('로그아웃 중 오류 발생:', error);
                alert('로그아웃을 실패했습니다.');
            })
    });
}