import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IRiskEpurationPoints } from '../risk-epuration-points.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../risk-epuration-points.test-samples';

import { RiskEpurationPointsService } from './risk-epuration-points.service';

const requireRestSample: IRiskEpurationPoints = {
  ...sampleWithRequiredData,
};

describe('RiskEpurationPoints Service', () => {
  let service: RiskEpurationPointsService;
  let httpMock: HttpTestingController;
  let expectedResult: IRiskEpurationPoints | IRiskEpurationPoints[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(RiskEpurationPointsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a RiskEpurationPoints', () => {
      const riskEpurationPoints = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(riskEpurationPoints).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RiskEpurationPoints', () => {
      const riskEpurationPoints = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(riskEpurationPoints).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RiskEpurationPoints', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RiskEpurationPoints', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RiskEpurationPoints', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRiskEpurationPointsToCollectionIfMissing', () => {
      it('should add a RiskEpurationPoints to an empty array', () => {
        const riskEpurationPoints: IRiskEpurationPoints = sampleWithRequiredData;
        expectedResult = service.addRiskEpurationPointsToCollectionIfMissing([], riskEpurationPoints);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(riskEpurationPoints);
      });

      it('should not add a RiskEpurationPoints to an array that contains it', () => {
        const riskEpurationPoints: IRiskEpurationPoints = sampleWithRequiredData;
        const riskEpurationPointsCollection: IRiskEpurationPoints[] = [
          {
            ...riskEpurationPoints,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRiskEpurationPointsToCollectionIfMissing(riskEpurationPointsCollection, riskEpurationPoints);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RiskEpurationPoints to an array that doesn't contain it", () => {
        const riskEpurationPoints: IRiskEpurationPoints = sampleWithRequiredData;
        const riskEpurationPointsCollection: IRiskEpurationPoints[] = [sampleWithPartialData];
        expectedResult = service.addRiskEpurationPointsToCollectionIfMissing(riskEpurationPointsCollection, riskEpurationPoints);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(riskEpurationPoints);
      });

      it('should add only unique RiskEpurationPoints to an array', () => {
        const riskEpurationPointsArray: IRiskEpurationPoints[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const riskEpurationPointsCollection: IRiskEpurationPoints[] = [sampleWithRequiredData];
        expectedResult = service.addRiskEpurationPointsToCollectionIfMissing(riskEpurationPointsCollection, ...riskEpurationPointsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const riskEpurationPoints: IRiskEpurationPoints = sampleWithRequiredData;
        const riskEpurationPoints2: IRiskEpurationPoints = sampleWithPartialData;
        expectedResult = service.addRiskEpurationPointsToCollectionIfMissing([], riskEpurationPoints, riskEpurationPoints2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(riskEpurationPoints);
        expect(expectedResult).toContain(riskEpurationPoints2);
      });

      it('should accept null and undefined values', () => {
        const riskEpurationPoints: IRiskEpurationPoints = sampleWithRequiredData;
        expectedResult = service.addRiskEpurationPointsToCollectionIfMissing([], null, riskEpurationPoints, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(riskEpurationPoints);
      });

      it('should return initial array if no RiskEpurationPoints is added', () => {
        const riskEpurationPointsCollection: IRiskEpurationPoints[] = [sampleWithRequiredData];
        expectedResult = service.addRiskEpurationPointsToCollectionIfMissing(riskEpurationPointsCollection, undefined, null);
        expect(expectedResult).toEqual(riskEpurationPointsCollection);
      });
    });

    describe('compareRiskEpurationPoints', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRiskEpurationPoints(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRiskEpurationPoints(entity1, entity2);
        const compareResult2 = service.compareRiskEpurationPoints(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRiskEpurationPoints(entity1, entity2);
        const compareResult2 = service.compareRiskEpurationPoints(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRiskEpurationPoints(entity1, entity2);
        const compareResult2 = service.compareRiskEpurationPoints(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
