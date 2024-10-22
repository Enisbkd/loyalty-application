import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'loyaltyApplicationApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'client',
    data: { pageTitle: 'loyaltyApplicationApp.client.home.title' },
    loadChildren: () => import('./client/client.routes'),
  },
  {
    path: 'loyalty-points',
    data: { pageTitle: 'loyaltyApplicationApp.loyaltyPoints.home.title' },
    loadChildren: () => import('./loyalty-points/loyalty-points.routes'),
  },
  {
    path: 'risk-epuration-points',
    data: { pageTitle: 'loyaltyApplicationApp.riskEpurationPoints.home.title' },
    loadChildren: () => import('./risk-epuration-points/risk-epuration-points.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
