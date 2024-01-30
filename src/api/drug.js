import requests from '@/utils/requests.js'

export const drugListService = ()=>{
    return requests.get('/drugAll');
}

// 药品查询
export const drugListSearchService = (pageData)=>{
    return requests.get('/druglist', {params:{page:pageData.page,pageSize:pageData.pageSize}});
}

export const barCodeQuaryService = (barCode)=>{
    console.log(barCode.value);
    return requests.get('/drugbybar/' + barCode.value);
}

export const drugNameQuaryService = (drugName)=>{
    console.log(drugName.value);
    return requests.get('/drugbyname/' + drugName.value);
}






