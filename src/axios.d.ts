import axios, { Axios, AxiosResponse, AxiosRequestConfig } from 'axios'

declare module 'axios' {
    interface AxiosResponse<T = any> {
        data: T;
        // 这个地方放属性
        msg: any
    }
    export function create(config?: AxiosRequestConfig): AxiosInstance
}