import requests from '@/utils/requests.js';
import axios from "axios";

export const salesListService = (salesInfo) => {
    return requests.post('/sales/history', salesInfo)
}

export const saleAdd = (saleForm) => {
    return requests.put('/sales/saleAdd', saleForm);
}
//销售统计页面
export const saleGetAll = () =>{
    return requests.get('/sales/salecount');
}

export const saleGetCount = (searchInfo) => {
    return requests.put('/sales/salecount/count',searchInfo);
}

export const saleGetAmount = (searchInfo) => {
    return requests.put('/sales/salecount/amount',searchInfo);
}
export const saleRevokeOrder = (revokeData) => {
    return requests.put('/sales/history/revoke',revokeData);
}