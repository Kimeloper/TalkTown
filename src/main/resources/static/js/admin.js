// 회원 강제 탈퇴
const deleteMemberButton = document.querySelectorAll(".deleteMember_btn");

if (deleteMemberButton) {
    deleteMemberButton.forEach(btn => {
        btn.addEventListener('click', event => {

            const tr = btn.closest('tr');
            const id = tr.querySelector('.member_id').textContent;
            const isConfirm = confirm("해당 회원을 강제 탈퇴 처리하시겠습니까?");

            if(!isConfirm){
                return;
            }

            fetch(`/admin/${id}`, {
                method: 'DELETE'
            })
            .then(() => {
                alert('강제 탈퇴처리 되었습니다.');
                location.replace('/admin');
            })
            .catch(error => {
                console.error("회원 강제 탈퇴 중 오류 발생:", error);
                alert("회원 강제 탈퇴에 실패했습니다.");
            });
        });
    });
}
