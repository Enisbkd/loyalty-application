import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRiskEpurationPoints } from '../risk-epuration-points.model';
import { RiskEpurationPointsService } from '../service/risk-epuration-points.service';

const riskEpurationPointsResolve = (route: ActivatedRouteSnapshot): Observable<null | IRiskEpurationPoints> => {
  const id = route.params.id;
  if (id) {
    return inject(RiskEpurationPointsService)
      .find(id)
      .pipe(
        mergeMap((riskEpurationPoints: HttpResponse<IRiskEpurationPoints>) => {
          if (riskEpurationPoints.body) {
            return of(riskEpurationPoints.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default riskEpurationPointsResolve;
