import http from 'k6/http';
import {check} from 'k6';

const token = __ENV.TEST_AUTH_TOKEN;
const userId = __ENV.TEST_USER_ID;
const userRole = __ENV.TEST_USER_ROLE;

export const options = {
  stages: [
    {duration: '10s', target: 100},
    {duration: '10s', target: 200},
    {duration: '10s', target: 300},
    {duration: '10s', target: 400},
    {duration: '10s', target: 500},
    {duration: '10s', target: 0},
  ],
  thresholds: {
    http_req_duration: ['p(95)<1000'],
  },
};

export default function () {
  const url = 'http://host.docker.internal:10000/v1/orders';

  const payload = JSON.stringify({
    order: {
      orderItems: [
        {
          id: 'f65a955a-abcd-4b5c-9cf4-149bd662e783',
          productName: 'Sample Product - A',
          price: 150000,
          quantity: 3,
        },
        {
          id: '3f6164db-5907-4cf0-afe4-c219527516ae',
          productName: 'Sample Product - B',
          price: 10000,
          quantity: 5,
        },
      ],
      shippingInfo: {
        receiverName: 'John Doe',
        receiverPhone: '010-1234-5678',
        zipCode: '12345',
        address: 'Shipping Address',
        addressDetail: 'Shipping Address Detail',
      },
      request: 'Request Example',
      orderName: 'Order Name Sample',
      couponId: 'e676a7e8-9ab4-4452-a41c-351b69bba2d1',
      totalAmount: 500000,
      discountAmount: 33333,
      finalAmount: 466667,
    },
  });

  const params = {
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
      'X-User-Id': userId,
      'X-User-Role': userRole
    },
  };

  const response = http.post(url, payload, params);

  check(response, {
    'status is 200': (r) => r.status === 200
  });

  if (response.status === 200) {
    console.log(`VU ${__VU}: 주문 성공 - 응답 시간: ${response.timings.duration}ms`);
  } else {
    console.log(
        `VU ${__VU}: 주문 실패 - 상태 코드: ${response.status}, 메시지: ${response.body}`);
  }
}
