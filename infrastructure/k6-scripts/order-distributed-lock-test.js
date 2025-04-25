import http from 'k6/http';
import {check, sleep} from 'k6';

const token = __ENV.TEST_AUTH_TOKEN;
const userId = __ENV.TEST_USER_ID;
const userRole = __ENV.TEST_USER_ROLE;

export const options = {
  scenarios: {
    concurrent_requests: {
      executor: 'constant-vus',
      vus: 100,
      duration: '10s'
    },
  }
};

export default function () {
  const url = 'http://host.docker.internal:10000/v2/orders';

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
        receiverName: 'Lock Test User',
        receiverPhone: '010-1234-5678',
        zipCode: '12345',
        address: 'Test Address',
        addressDetail: 'Test Address Detail',
      },
      request: `분산락 테스트 주문 - ${__VU} 번 사용자`,
      orderName: `분산락 테스트 주문 - ${__VU} 번 사용자`,
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
      'X-USER-ID': userId,
      'X-USER-ROLE': userRole
    },
  };

  const response = http.post(url, payload, params);

  check(response, {
    'status is 200': (r) => r.status === 200,
  });

  if (response.status === 200) {
    console.log(`VU ${__VU}: 주문 성공 - 응답 시간: ${response.timings.duration}ms`);
  } else {
    console.log(
        `VU ${__VU}: 주문 실패 - 상태 코드: ${response.status}, 메시지: ${response.body}`);
  }

  sleep(Math.random());
}