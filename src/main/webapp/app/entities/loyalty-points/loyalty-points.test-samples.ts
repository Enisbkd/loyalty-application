import { ILoyaltyPoints, NewLoyaltyPoints } from './loyalty-points.model';

export const sampleWithRequiredData: ILoyaltyPoints = {
  id: 23723,
};

export const sampleWithPartialData: ILoyaltyPoints = {
  id: 27465,
  myPoints: 3487,
};

export const sampleWithFullData: ILoyaltyPoints = {
  id: 24957,
  statusPoints: 25293,
  myPoints: 4608,
};

export const sampleWithNewData: NewLoyaltyPoints = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
