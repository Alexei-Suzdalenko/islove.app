let userData = {}
if(urlPage.length > 7){
    document.getElementById('listUsersContent').remove() 
    document.getElementById('wraperNavigation').remove()

    let pageUtl = window.location.pathname
    let userIdArray = pageUtl.split('/')
    let userId = userIdArray[userIdArray.length-1]
    
    if(userId == 'index.html') userId = 'xH88FVoldHPXZ9mA7vnkICkcJbt1'

    db.collection('user').doc(userId).get().then((querySnapshot) => {
        let small = querySnapshot.data()
        userData = {
            name: small.name,
            email: small.email,
            age: small.age, 
            back: small.back,
            country: small.country,
            gender: small.gender, 
            id: small.id,
            image: small.image,
            status: small.status
        }
        document.title = userData.name  + ' ' + userData.email + ' | LOVE CHAT' 
        insertUserDataInPage()
        document.getElementById('loader').remove()
    })
}

function insertUserDataInPage(){
    let userItemPage = document.getElementById('userItemPage')

    let userPageInfo = `
        <h1><a href="/love-chat/`+userData.id+`" target="_blank" title="`+userData.name+`"><b>`+userData.name+`</b></a></h1>
        <img src="`+userData.image+`" alt="`+userData.name+` `+userData.email+`" class="w3-image w3-padding-32" width="60%" height="auto">
        <div class="w3-content w3-justify" style="max-width:600px">
            <p>Email: `+userData.email+`</p>
            <p>Age: `+userData.age+`</p>
            <p>Status: `+userData.status+`</p>
        </div>
        <img src="`+userData.back+`" alt="`+userData.name+` `+userData.email+`" class="w3-image w3-padding-32" width="100%" height="auto">`

    userItemPage.innerHTML = userPageInfo    
}