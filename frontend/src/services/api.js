import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

// 기본 axios 인스턴스 생성
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export default apiClient;