
import Main from '@/components/main';

import { emailSetting } from './email';
import { notice } from './notice';
import { summaryText } from './text-summarization';
// 业务
export const business = [
  {
    path: '/business',
    name: 'Business',
    component: Main,
    meta: {
      title: '业务功能',
      topMenu:true,
      icon: 'icon iconfont iconyewugongneng'
    },
    children: [
      ...summaryText,
      ...emailSetting,
      // ...keepAlive,
      ...notice
      
    ]
  }
];
