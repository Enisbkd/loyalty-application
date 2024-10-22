import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IRiskEpurationPoints } from '../risk-epuration-points.model';
import { RiskEpurationPointsService } from '../service/risk-epuration-points.service';

import riskEpurationPointsResolve from './risk-epuration-points-routing-resolve.service';

describe('RiskEpurationPoints routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: RiskEpurationPointsService;
  let resultRiskEpurationPoints: IRiskEpurationPoints | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(RiskEpurationPointsService);
    resultRiskEpurationPoints = undefined;
  });

  describe('resolve', () => {
    it('should return IRiskEpurationPoints returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        riskEpurationPointsResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultRiskEpurationPoints = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultRiskEpurationPoints).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        riskEpurationPointsResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultRiskEpurationPoints = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultRiskEpurationPoints).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IRiskEpurationPoints>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        riskEpurationPointsResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultRiskEpurationPoints = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultRiskEpurationPoints).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
