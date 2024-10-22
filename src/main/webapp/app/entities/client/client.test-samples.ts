import { IClient, NewClient } from './client.model';

export const sampleWithRequiredData: IClient = {
  id: 31019,
};

export const sampleWithPartialData: IClient = {
  id: 6935,
  firstName: 'Cedrick',
  email: 'Khalil2@gmail.com',
  dateOfBirth: 'that of',
  signUpDate: 'lucky valentine',
};

export const sampleWithFullData: IClient = {
  id: 21981,
  firstName: 'Granville',
  lastName: 'Cremin',
  email: 'Brittany_Ruecker73@gmail.com',
  dateOfBirth: 'shadowbox brochure wrongly',
  emailVerified: true,
  signUpDate: 'tribe alb',
};

export const sampleWithNewData: NewClient = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
