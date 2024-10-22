import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '02989d30-fca7-4648-8b77-7e8ca359c08b',
};

export const sampleWithPartialData: IAuthority = {
  name: '5a104734-3b8b-4e88-963a-ff6559a05ba0',
};

export const sampleWithFullData: IAuthority = {
  name: 'cbfc91a9-574c-40fa-8f2b-c424fbc7cb63',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
