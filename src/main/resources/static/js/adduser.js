const nameNew = document.getElementById('nameNew');
const passwordNew = document.getElementById('passwordNew');

const formAdd = document.getElementById('formNewUser');


formAdd.onsubmit = async (e) => {
    e.preventDefault();

    let arrayAdd = [];
    let optionsAdd = document.querySelector('#rolesNew').options;
    for (let i = 0; i < optionsAdd.length; i++) {
        if (optionsAdd[i].selected) {
            arrayAdd.push(allroles[i])
        }
    }

    console.log(nameNew.value);
    console.log(passwordNew.value);
    console.log(arrayAdd);
    let response = await fetch('/api',{
        method: 'POST',
        body: JSON.stringify({
            username: nameNew.value,
            password: passwordNew.value,
            roles: arrayAdd
        }),
        headers: {
            'Content-type': 'application/json'
        }}).catch(err => console.log(err));

    if(response.ok) {
        fillUsersTable();
    }
    formAdd.reset();
    // document.getElementById('users-block').click();
};
