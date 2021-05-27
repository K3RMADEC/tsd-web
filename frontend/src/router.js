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
        // Login
        {
          meta: {
            public: true,
          },
          name: 'Login',
          path: '',
          component: () => import('@/views/login/Login'),
        },
        // Dashboard
        {
          meta: {
            public: false,
          },
          name: 'Dashboard',
          path: '/dashboard',
          component: () => import('@/views/dashboard/Dashboard'),
        },
        // Pages
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
          name: 'User Profile',
          path: 'pages/user-profile',
          component: () => import('@/views/dashboard/pages/UserProfile'),
        },
        {
          meta: {
            public: false,
          },
          name: 'Notifications',
          path: 'components/notifications',
          component: () => import('@/views/dashboard/component/Notifications'),
        },
        {
          meta: {
            public: false,
          },
          name: 'Icons',
          path: 'components/icons',
          component: () => import('@/views/dashboard/component/Icons'),
        },
        {
          meta: {
            public: false,
          },
          name: 'Typography',
          path: 'components/typography',
          component: () => import('@/views/dashboard/component/Typography'),
        },
        // Tables
        {
          meta: {
            public: false,
          },
          name: 'Regular Tables',
          path: 'tables/regular-tables',
          component: () => import('@/views/dashboard/tables/RegularTables'),
        },
        // Maps
        {
          meta: {
            public: false,
          },
          name: 'Google Maps',
          path: 'maps/google-maps',
          component: () => import('@/views/dashboard/maps/GoogleMaps'),
        },
        {
          meta: {
            public: false,
          },
          name: 'Map',
          path: 'maps/leaflet-map',
          component: () => import('@/views/dashboard/maps/LeafletMap'),
        },
        {
          meta: {
            public: false,
          },
          name: 'buttons',
          path: 'buttons',
          component: () => import('@/views/dashboard/component/Buttons'),
        },
        {
          meta: {
            public: false,
          },
          name: 'grid',
          path: 'grid',
          component: () => import('@/views/dashboard/component/Grid'),
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
