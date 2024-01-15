const imageContainer = document.querySelector("monkey-container");
const monkeyEyesClose = document.querySelector("#monkey-eyes-close");
const monkeyEyesOpen = document.querySelector("#monkey-eyes-open");

const passwordInput = document.querySelector("#password");
const visibilityToggler = document.querySelector("#toggle-visible");
const submitButton = document.querySelector("#submit-button");

const FAKE_PASSWORD = "12345678";

const togglePassword = (e) => {
  if (passwordInput.classList.contains("password-visible")) {
    visibilityToggler.innerHTML = `<img src="../static/images/eye.svg" alt="Eye" />`;
    monkeyEyesClose.classList.add("is-hidden");
    monkeyEyesOpen.classList.remove("is-hidden");
  } else {
    visibilityToggler.innerHTML = `<img src="../static/images/eye-slash.svg" alt="Eye Slash" />`;
    monkeyEyesClose.classList.remove("is-hidden");
    monkeyEyesOpen.classList.add("is-hidden");
  }

  const type =
    passwordInput.getAttribute("type") === "password" ? "text" : "password";
  passwordInput.setAttribute("type", type);

  passwordInput.classList.toggle("password-visible");
};


const checkPassword = (e) => {
  const password = passwordInput.value.trim();
  const passwordIsEmpty = password.length === 0;

  passwordInput.classList.remove("is-invalid");

  if (passwordIsEmpty) {
    passwordInput.classList.remove("is-not-empty");
  } else {
    passwordInput.classList.add("is-not-empty");
  }
};

visibilityToggler.addEventListener("click", togglePassword);

passwordInput.addEventListener("input", () => {
  checkPassword();
});


