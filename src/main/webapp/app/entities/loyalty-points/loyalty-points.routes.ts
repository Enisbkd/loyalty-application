import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import LoyaltyPointsResolve from './route/loyalty-points-routing-resolve.service';

const loyaltyPointsRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/loyalty-points.component').then(m => m.LoyaltyPointsComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/loyalty-points-detail.component').then(m => m.LoyaltyPointsDetailComponent),
    resolve: {
      loyaltyPoints: LoyaltyPointsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/loyalty-points-update.component').then(m => m.LoyaltyPointsUpdateComponent),
    resolve: {
      loyaltyPoints: LoyaltyPointsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/loyalty-points-update.component').then(m => m.LoyaltyPointsUpdateComponent),
    resolve: {
      loyaltyPoints: LoyaltyPointsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default loyaltyPointsRoute;
