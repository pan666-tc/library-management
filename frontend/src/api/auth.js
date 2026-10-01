import request from './request'

export const register = (username, password, phone) => {
  return request.post('/auth/register', null, {
    params: { username, password, phone }
  })
}

export const login = (username, password) => {
  return request.post('/auth/login', null, {
    params: { username, password }
  })
}
