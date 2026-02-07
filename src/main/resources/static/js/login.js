const loginButton = document.getElementById('submit_btn');

if(loginButton){
    loginButton.addEventListener('click', event => {
        event.preventDefault();

        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value.trim();

        if(!email && !password){
            alert("이메일과 비밀번호를 입력해주세요.");
            return;
        }else if(!email){
            alert("이메일을 입력해주세요.")
            return;
        }else if(!password){
            alert("비밀번호를 입력해주세요.")
            return;
        }

        fetch(`/api/login`, {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json',
            },
            body: JSON.stringify({
                email:email,
                password: password
                })
            })
            .then(response => {
                if(response.ok){
                    location.replace('/main');
                }else{
                    alert('로그인을 실패했습니다.');
                    return;
                }
            })
            .catch(error => {
                console.error('로그인 중 오류 발생:', error);
                alert('로그인을 실패했습니다.');
            });
        });
}