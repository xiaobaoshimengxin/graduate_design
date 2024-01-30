import requests from '@/utils/requests.js'

export const adminLoginService = (LoginData) => {
    return requests.post('/login', LoginData);
}

export const resetPasswordService = (resetInfo)=>{
    return requests.put('/resetPassword', resetInfo);
}


