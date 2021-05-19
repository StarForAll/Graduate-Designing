import Main from '@/components/main';
// 自动摘要-路由
export const summaryText = [
    {
        path: '/summary',
        name: 'summaryText',
        component: Main,
        meta: {
            title: '自动摘要管理',
            icon: 'icon iconfont iconpv_Literature'
        },
        children: [
            //  论文 列表
            {
                path: '/summary/auto',
                name: 'summaryList1',
                meta: {
                    title: '中文摘要生成',
                    privilege: [
                        { title: '查询', name: 'summary-summaryPage-query' },
                        { title: '新增', name: 'summary-summaryPage-upload' },
                        { title: '获取自动摘要信息', name: 'summary-summaryPage-getContent' },
                        { title: '删除', name: 'summary-summaryPage-delete' }
                    ]
                },
                component: () => import('@/views/business/summary/summary.vue')
            },
            {
                path: '/summary/auto2',
                name: 'summaryList2',
                meta: {
                    title: '摘要管理',
                    privilege: [
                        { title: '查询', name: 'summary-summaryPage-query1' },
                        { title: '新增', name: 'summary-summaryPage-upload1' },
                        { title: '获取自动摘要信息', name: 'summary-summaryPage-getContent1' },
                        { title: '删除', name: 'summary-summaryPage-delete1' }
                    ]
                },
                component: () => import('@/views/business/summary/summary.vue')
            }
        ]
    }
];
