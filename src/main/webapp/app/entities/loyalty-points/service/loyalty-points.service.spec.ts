import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ILoyaltyPoints } from '../loyalty-points.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../loyalty-points.test-samples';

import { LoyaltyPointsService } from './loyalty-points.service';

const requireRestSample: ILoyaltyPoints = {
  ...sampleWithRequiredData,
};

describe('LoyaltyPoints Service', () => {
  let service: LoyaltyPointsService;
  let httpMock: HttpTestingController;
  let expectedResult: ILoyaltyPoints | ILoyaltyPoints[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(LoyaltyPointsService);
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

    it('should create a LoyaltyPoints', () => {
      const loyaltyPoints = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(loyaltyPoints).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LoyaltyPoints', () => {
      const loyaltyPoints = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(loyaltyPoints).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LoyaltyPoints', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LoyaltyPoints', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LoyaltyPoints', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLoyaltyPointsToCollectionIfMissing', () => {
      it('should add a LoyaltyPoints to an empty array', () => {
        const loyaltyPoints: ILoyaltyPoints = sampleWithRequiredData;
        expectedResult = service.addLoyaltyPointsToCollectionIfMissing([], loyaltyPoints);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loyaltyPoints);
      });

      it('should not add a LoyaltyPoints to an array that contains it', () => {
        const loyaltyPoints: ILoyaltyPoints = sampleWithRequiredData;
        const loyaltyPointsCollection: ILoyaltyPoints[] = [
          {
            ...loyaltyPoints,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLoyaltyPointsToCollectionIfMissing(loyaltyPointsCollection, loyaltyPoints);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LoyaltyPoints to an array that doesn't contain it", () => {
        const loyaltyPoints: ILoyaltyPoints = sampleWithRequiredData;
        const loyaltyPointsCollection: ILoyaltyPoints[] = [sampleWithPartialData];
        expectedResult = service.addLoyaltyPointsToCollectionIfMissing(loyaltyPointsCollection, loyaltyPoints);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loyaltyPoints);
      });

      it('should add only unique LoyaltyPoints to an array', () => {
        const loyaltyPointsArray: ILoyaltyPoints[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const loyaltyPointsCollection: ILoyaltyPoints[] = [sampleWithRequiredData];
        expectedResult = service.addLoyaltyPointsToCollectionIfMissing(loyaltyPointsCollection, ...loyaltyPointsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const loyaltyPoints: ILoyaltyPoints = sampleWithRequiredData;
        const loyaltyPoints2: ILoyaltyPoints = sampleWithPartialData;
        expectedResult = service.addLoyaltyPointsToCollectionIfMissing([], loyaltyPoints, loyaltyPoints2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loyaltyPoints);
        expect(expectedResult).toContain(loyaltyPoints2);
      });

      it('should accept null and undefined values', () => {
        const loyaltyPoints: ILoyaltyPoints = sampleWithRequiredData;
        expectedResult = service.addLoyaltyPointsToCollectionIfMissing([], null, loyaltyPoints, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loyaltyPoints);
      });

      it('should return initial array if no LoyaltyPoints is added', () => {
        const loyaltyPointsCollection: ILoyaltyPoints[] = [sampleWithRequiredData];
        expectedResult = service.addLoyaltyPointsToCollectionIfMissing(loyaltyPointsCollection, undefined, null);
        expect(expectedResult).toEqual(loyaltyPointsCollection);
      });
    });

    describe('compareLoyaltyPoints', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLoyaltyPoints(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLoyaltyPoints(entity1, entity2);
        const compareResult2 = service.compareLoyaltyPoints(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLoyaltyPoints(entity1, entity2);
        const compareResult2 = service.compareLoyaltyPoints(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLoyaltyPoints(entity1, entity2);
        const compareResult2 = service.compareLoyaltyPoints(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
