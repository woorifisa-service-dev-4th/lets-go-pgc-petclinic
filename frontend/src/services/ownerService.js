import apiClient from './api';

export const ownerService = {
  // 모든 소유자 또는 lastName으로 검색
  getOwners(lastName = '', page = 0, size = 10) {
    return apiClient.get('/owners', { 
      params: { 
        lastName: lastName || undefined,
        page,
        size
      } 
    });
  },

  // 소유자 상세 정보 가져오기
  getOwnerById(id) {
    return apiClient.get(`/owners/${id}`);
  },

  // 새 소유자 생성
  createOwner(ownerData) {
    return apiClient.post('/owners', ownerData, {
      validateStatus: function (status) {
        // 201 Created 상태 코드를 성공으로 간주
        return status >= 200 && status < 300 || status === 201;
      }
    });
  },

  // 소유자 정보 업데이트
  updateOwner(id, ownerData) {
    return apiClient.put(`/owners/${id}`, ownerData);
  },

  // 소유자 성으로 검색
  searchOwnersByLastName(lastName) {
    return apiClient.get(`/owners/search`, { params: { lastName } });
  }
};