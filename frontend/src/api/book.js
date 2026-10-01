import request from './request'

export const findAll = (pageNum = 1, pageSize = 10) => {
  return request.get('/book/list', { params: { pageNum, pageSize } })
}

export const findBook = (id) => {
  return request.get('/book/' + id)
}

export const searchByTitle = (title) => {
  return request.get('/book/search', { params: { title } })
}
