import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import RiskEpurationPointsResolve from './route/risk-epuration-points-routing-resolve.service';

const riskEpurationPointsRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/risk-epuration-points.component').then(m => m.RiskEpurationPointsComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/risk-epuration-points-detail.component').then(m => m.RiskEpurationPointsDetailComponent),
    resolve: {
      riskEpurationPoints: RiskEpurationPointsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/risk-epuration-points-update.component').then(m => m.RiskEpurationPointsUpdateComponent),
    resolve: {
      riskEpurationPoints: RiskEpurationPointsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/risk-epuration-points-update.component').then(m => m.RiskEpurationPointsUpdateComponent),
    resolve: {
      riskEpurationPoints: RiskEpurationPointsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default riskEpurationPointsRoute;
