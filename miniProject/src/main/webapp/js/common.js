/**
 * 
 */

const formToSerialize = (formId) => {
    const formData = {};
    const form = document.querySelector('#' + formId);
    const formElements = form.elements;

    for (let i = 0; i < formElements.length; i++) {
        const element = formElements[i];

        if (element.name === '') continue;

        if (element.type === 'radio' || element.type === 'checkbox') {
            if (element.checked) {
                if (!formData[element.name]) {
                    formData[element.name] = [];
                }
                formData[element.name].push(element.value);
            }
        } else {
            formData[element.name] = element.value;
        }
    }

    return JSON.stringify(formData);
};

/*
url : 서버의 주소
formId : formId 또는 json 연관배열
handler : 서버에서 결과를 전달해주면 받아서 처리하는 함수 
*/

const ybFetch = (url, formId, handler) => {
    const param = typeof formId == "string" ? formToSerialize(formId) : JSON.stringify(formId);
    fetch(url, {
        method: "POST",
        body: param,
        headers: { "Content-type": "application/json; charset=utf-8" }
    })
    .then(res => {
        if (res.ok) {
            // 성공적인 응답을 받은 경우
            return res.json(); // JSON 데이터를 반환하는 함수를 호출하여 JSON 데이터를 얻어옴
        } else {
            // 오류 응답을 받은 경우
            throw new Error("실패");
        }
    })
    .then(json => {
        // 서버로부터 받은 결과를 사용해서 처리 루틴 구현  
        console.log("json ", json);
        if (handler) handler(json);
    })
    .catch(error => {
        // 오류 발생 시 처리할 로직
        console.error('실패', error.message);
    });
}