import request from './request'

export const borrowBook = (bookId) => {
  return request.post('/borrow/borrow', null, { params: { bookId } })
}

export const returnBook = (bookId) => {
  return request.post('/borrow/return', null, { params: { bookId } })
}

export const getHistory = (pageNum = 1, pageSize = 10) => {
  return request.get('/borrow/history', { params: { pageNum, pageSize } })
}
