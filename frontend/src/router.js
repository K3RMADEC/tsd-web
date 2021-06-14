import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      component: () => import('@/views/Index'),
      children: [
        {
          meta: {
            public: true,
          },
          name: 'Login',
          path: '',
          component: () => import('@/views/login/Login'),
        },
        {
          meta: {
            public: false,
          },
          name: 'Dashboard',
          path: '/dashboard',
          component: () => import('@/views/dashboard/Dashboard'),
        },
        {
          meta: {
            public: false,
          },
          name: 'Users Managment',
          path: 'pages/users',
          component: () => import('@/views/dashboard/pages/Users'),
        },
        {
          meta: {
            public: false,
          },
          name: 'Historic',
          path: 'pages/historic',
          component: () => import('@/views/dashboard/pages/Historic'),
        },
        {
          meta: {
            public: false,
          },
          name: 'Connector',
          path: 'pages/connector',
          component: () => import('@/views/dashboard/pages/Connector'),
        },
        {
          meta: {
            public: false,
          },
          name: 'Map',
          path: 'maps/leaflet-map',
          component: () => import('@/views/dashboard/maps/LeafletMap'),
        },
      ],
    },
  ],
})

router.beforeEach((to, from, next) => {
  const authRequired = !to.meta.public
  const loggedIn = localStorage.getItem('token')

  // trying to access a restrited page without logged in redirecto to login page
  if (authRequired && !loggedIn) {
    next('/')
  } else {
    next()
  }
})

export default router
