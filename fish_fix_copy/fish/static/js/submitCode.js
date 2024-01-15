const imageContainer = document.querySelector(".monkey-container");
const monkeyWatchUser = document.querySelector("#monkey-watch-user");
const monkeyWatchCode = document.querySelector("#monkey-watch-code");

const codeInput = document.querySelector("#code");
const codeInputLabel = document.querySelector("#code-label");

const submitButton = document.querySelector("#submit-button");

// const FAKE_CODE = "12345";

const updateImage = () => {
  if (codeInput.value.trim() != "") {
    monkeyWatchUser.classList.add("is-hidden");
    monkeyWatchCode.classList.remove("is-hidden");
  } else {
    monkeyWatchUser.classList.remove("is-hidden");
    monkeyWatchCode.classList.add("is-hidden");
  }
};


const checkCode = () => {
  const code = codeInput.value.trim();

  const userLang = navigator.language || navigator.userLanguage;
  const lang = userLang.split("-")[0];

  if (code.length === codeInput.maxLength) {
    codeInput.classList.add("is-not-empty");

    // if (code !== FAKE_CODE) {
    //   codeInput.classList.add("is-invalid");

    //   if (lang == "ru") {
    //     codeInputLabel.innerText = "Некорректный код телефона";
    //   } else {
    //     codeInputLabel.innerText = "Invalid code";
    //   }

    //   submitButton.classList.add("is-disabled");
    // } else {
    //   codeInput.classList.remove("is-invalid");

    //   if (lang == "ru") {
    //     codeInputLabel.innerText = "Код";
    //   } else {
    //     codeInputLabel.innerText = "Code";
    //   }

    //   submitButton.classList.remove("is-disabled");
    // }

    codeInput.classList.remove("is-invalid");

    if (lang == "ru") {
      codeInputLabel.innerText = "Код";
    } else {
      codeInputLabel.innerText = "Code";
    }

    submitButton.classList.remove("is-disabled");

  } else if (code.length === 0) {
    codeInput.classList.remove("is-not-empty");
    codeInput.classList.remove("is-invalid");

    if (lang == "ru") {
      codeInputLabel.innerText = "Код";
    } else {
      codeInputLabel.innerText = "Code";
    }

    submitButton.classList.add("is-disabled");
  } else {
    codeInput.classList.add("is-not-empty");
    codeInput.classList.remove("is-invalid");

    if (lang == "ru") {
      codeInputLabel.innerText = "Код";
    } else {
      codeInputLabel.innerText = "Code";
    }

    submitButton.classList.add("is-disabled");
  }
};

codeInput.addEventListener("input", () => {
  updateImage();
  checkCode();
});
