import requests from '@/utils/requests.js'

export const salesListService = (salesInfo) => {
    return requests.post('/sales/history', salesInfo)
}

export const saleAdd = (saleForm) =>{
    return requests.put('/sales/saleAdd', saleForm);
}