import requests from '@/utils/requests.js'

export const supplierListService = (supWord) => {
    return requests.get('/manage/suplist?supWord=' + supWord);
}

export const supAddService = (sup) => {
    return requests.post('/manage/supplier', sup)
}

export const supUpdateService = (sup)=>{
    return requests.put('/manage/supplier', sup)
}
// 查询员工
export const employeeListService = ()=>{
    return requests.get('/manage/employee');
}
// 新增员工
export const empAddService = (emp)=>{
    return requests.post('/manage/employee', emp);
}
// 修改员工
export const empUpdateService = (emp)=>{
    return requests.put('/manage/employee', emp);
}

// 列出所有顾客信息
export const customerListService = ()=>{
    return requests.get('/manage/customer');
}
// 关键字查询
export const customerQueryService = (char)=>{
    return requests.get('/manage/customer/'+char);
}
// 新增顾客
export const customerAdd = (cusdata)=>{
    return requests.post('/manage/customer', cusdata);
}
// 积分操作
export const customerScoreOperationService = (opData)=>{
    return requests.put('/manage/customer', opData);
}

