let listUsersContent = document.getElementById('listUsersContent');

class User{
    constructor(name, email, image, age, status, id){
        this.name   = name;
        this.email  = email;
        this.image  = image;
        this.age    = age || '';
        this.status = status; this.id = id;
    }
}
var listUsers = []

let urlPage = window.location.pathname

/* estoy en la pagina '/' */

if(urlPage.length < 5 || urlPage == '/' || urlPage == ''){  // .limit(999)
    document.getElementById('userItemPage').remove()

    db.collection('user').where('name', '!=', null).get().then((querySnapshot) => {

        querySnapshot.forEach((doc) => {
           let small = doc.data();
            if(small.email && small.image){
               let user = new User(small.name, small.email, small.image, small.age, small.status, small.id);            console.log(user)
               let userImage = `<a href="/love-chat/`+user.id+`" target="_blank"><img src="`+user.image+`" class="userImageItem" alt="`+user.name+` `+user.email+`"></a>`
               listUsers.push(userImage) 
            }
        })
        document.getElementById('loader').remove()
        showUsersThums()
        showUserLinks()
    })
}



function showUsersThums(){
    let imagesRes = ''
    for(let i = 0; i < listUsers.length; i++){ imagesRes += listUsers[i] }
    listUsersContent.innerHTML = imagesRes
}



function showUserLinks(){
    let usersNavigationLinks = document.getElementById('usersNavigationLinks');
    if(usersNavigationLinks){
        let linksUsers = `<a href="#" class="w3-bar-item w3-button w3-hover-black">«</a>
                          <a href="#" class="w3-bar-item w3-black w3-button">1</a>
                          <a href="#" class="w3-bar-item w3-button w3-hover-black">2</a>
                          <a href="#" class="w3-bar-item w3-button w3-hover-black">3</a>
                          <a href="#" class="w3-bar-item w3-button w3-hover-black">4</a>
                          <a href="#" class="w3-bar-item w3-button w3-hover-black">»</a>`;
        usersNavigationLinks.innerHTML = linksUsers;
    }
}









/*
const node = document.createElement('div');
node.innerHTML = linksUsers
listUsersContent.appendChild(node);

*/