let password = document.getElementById('password');
let ojo = document.getElementById('ojo');
let click = false;
ojo.addEventListener('click', (e) => {
	if (!click) {
		password.type = 'text'
		click = true
	} else if (click) {
		password.type = 'password'
		click = false
	}
	if (ojo.className == 'bi bi-eye') {
		ojo.className = 'bi bi-eye-slash';
	} else {
		ojo.className = 'bi bi-eye';
	}
})




