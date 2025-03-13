import apiClient from './api';

export const petService = {
  // 모든 펫 타입 가져오기
  getPetTypes() {
    return apiClient.get('/pets/types');
  },

  // 펫 상세 정보 가져오기
  getPetById(id) {
    return apiClient.get(`/pets/${id}`);
  },

  // 새 펫 생성
  createPet(ownerId, petData) {
    return apiClient.post(`/owners/${ownerId}/pets`, petData);
  },

  // 펫 정보 업데이트
  updatePet(id, petData) {
    return apiClient.put(`/pets/${id}`, petData);
  }
};