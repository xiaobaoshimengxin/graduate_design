import requests from '@/utils/requests.js'

export const purchaseHistoryList = (dateAndSupllier) => {
    return requests.post('/purchase/history', dateAndSupllier);
}

export const purchaseAdd = (formData)=>{
    return requests.post('/purchase/purchaseadd', formData);
}


