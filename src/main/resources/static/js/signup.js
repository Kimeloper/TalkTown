//이메일 검사
document.addEventListener('DOMContentLoaded', event => {
  const emailInput = document.getElementById('email');
  const emailError = document.querySelector('.inputBox .emailError');

  emailInput.addEventListener('input', event => {

      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

      if(emailInput.value.trim() == ''){//입력 안했을때
          emailInput.classList.remove('valid');
          emailInput.classList.remove('invalid');
          emailError.textContent = '';
      }else if(emailPattern.test(emailInput.value)){//패턴과 일치할때
          emailInput.classList.add('valid');
          emailInput.classList.remove('invalid');
          emailError.textContent = '';

          fetch(`/checkEmail?email=${encodeURIComponent(emailInput.value.trim())}`)
            .then(response => response.json())
            .then(checkEmail => {
                if(checkEmail){//중복일때(true)
                    emailInput.classList.add('invalid');
                    emailInput.classList.remove('valid');
                    emailError.textContent = '이미 등록된 이메일입니다.';
                }else{//중복 아닐때(false)
                     emailInput.classList.add('valid');
                     emailInput.classList.remove('invalid');
                     emailError.textContent = '';
                }
            })
      }else{//불일치 할때
          emailInput.classList.add('invalid');
          emailInput.classList.remove('valid');
          emailError.textContent = '올바른 이메일 형식이 아닙니다.';
      }
  });
});

//작성자 검사
document.addEventListener('DOMContentLoaded', event => {
    const authorInput = document.getElementById('author');
    const authorError = document.querySelector('.inputBox .authorError');

    authorInput.addEventListener('input', event => {
        const authorPattern = /^[가-힣a-zA-Z]{2,8}$/;

      if(authorInput.value.trim() == ''){
            authorInput.classList.remove('valid');
            authorInput.classList.remove('invalid');
            authorError.textContent = '';
       }else if(authorPattern.test(authorInput.value)){
            authorInput.classList.remove('invalid');
            authorInput.classList.add('valid');
            authorError.textContent = '';

            fetch(`/checkAuthor?author=${encodeURIComponent(authorInput.value.trim())}`)
            .then(response => response.json())
            .then(checkAuthor => {
                if(checkAuthor){//중복일때(true)
                     authorInput.classList.add('invalid');
                     authorInput.classList.remove('valid');
                     authorError.textContent = '이미 사용중인 닉네임입니다.';
                }else{
                    authorInput.classList.add('valid');
                    authorInput.classList.remove('invalid');
                    authorError.textContent = '';
                }
            })
        }else{
            authorInput.classList.add('invalid');
            authorInput.classList.remove('valid');
            authorError.textContent ='2~8자, 한글/영문/숫자 사용 가능합니다.'
        }
    })
})

//비밀번호 검사
document.addEventListener('DOMContentLoaded', event => {
    const passwordInput = document.getElementById('password');
    const passwordError = document.querySelector('.inputBox .passwordError');

    passwordInput.addEventListener('input', event => {
        const passwordPattern = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{6,12}$/;

        if(passwordInput.value.trim()==''){
            passwordInput.classList.remove('valid');
            passwordInput.classList.remove('invalid');
            passwordError.textContent = '';
        }else if(passwordPattern.test(passwordInput.value)){
            passwordInput.classList.add('valid');
            passwordInput.classList.remove('invalid');
            passwordError.textContent = '';
        }else{
            passwordInput.classList.remove('valid');
            passwordInput.classList.add('invalid');
            passwordError.textContent = '영문, 숫자, 특수문자를 각각 최소 1개씩 포함해야 합니다.';
        }
    })
})

// 랜덤
document.getElementById("randomButton").addEventListener("click", function() {
    fetch("/api/nickname")
        .then(response => response.text())
        .then(data => {
            const authorInput = document.getElementById("author");
            authorInput.value = data;

            authorInput.dispatchEvent(new Event("input"));
            authorInput.dispatchEvent(new Event("change"));
            authorInput.dispatchEvent(new Event("blur"));
        });
});