/*회원 삭제*/
const deleteAccountButton = document.getElementById('deleteAccount_btn');
const checkForm = document.querySelector('.checkPasswordForm');
const okButton = document.getElementById('okDelete_btn');
const cancelButton = document.getElementById('cancel_btn');

if (deleteAccountButton) {
    deleteAccountButton.addEventListener('click', event => {
      checkForm.style.display = "flex";
    });

    cancelButton.addEventListener('click', event => {
       checkForm.style.display = "none";
    });

    okButton.addEventListener('click', event => {
        const password = document.getElementById('checkPassword').value;
        const isConfirm = confirm("정말 탈퇴를 하시겠습니까?\n(탈퇴를 진행하면 되돌릴 수 없습니다.)");

        if(!isConfirm){
            return;
        }

        fetch(`/deleteMember`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: new URLSearchParams({ password })
        })
        .then(response => {
            if (response.ok) {
                alert('그동안 서비스를 이용해주셔서 감사합니다.');
                location.href = '/main';
            }else{
                alert('비밀번호가 틀렸습니다. 다시 입력해주세요.');
                document.getElementById('checkPassword').value = '';
                checkForm.style.display = 'flex';
            }
        })
        .catch(error => {
            console.error('탈퇴 중 오류 발생:', error);
            alert('탈퇴를 실패했습니다.');
        });
    });
}

