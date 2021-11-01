import axios from 'axios';

export default axios.create({
  // baseURL: 'http://localhost:8080',
  baseURL: 'http://3.38.99.194:8080',
  headers: {
    'Content-type': 'application/json',
  },
});

// export const chatURL = 'http://localhost:8080/playssafy';
export const chatURL = 'http://3.38.99.194:8080/example';
