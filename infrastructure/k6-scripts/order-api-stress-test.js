import http from 'k6/http';
import {check} from 'k6';

const token = __ENV.TEST_AUTH_TOKEN;
const userId = __ENV.TEST_USER_ID;
const userRole = __ENV.TEST_USER_ROLE;

export const options = {
  scenarios: {
    ramping_test: {
      executor: 'per-vu-iterations',
      vus: 1000,
      iterations: 10,
      startTime: '0s',
      maxDuration: '1m',
    },
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
    'status is 200': (r) => r.status === 200,
    'response time < 500ms': (r) => r.timings.duration < 500
  });
}
