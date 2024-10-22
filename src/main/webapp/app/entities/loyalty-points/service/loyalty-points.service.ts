import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILoyaltyPoints, NewLoyaltyPoints } from '../loyalty-points.model';

export type PartialUpdateLoyaltyPoints = Partial<ILoyaltyPoints> & Pick<ILoyaltyPoints, 'id'>;

export type EntityResponseType = HttpResponse<ILoyaltyPoints>;
export type EntityArrayResponseType = HttpResponse<ILoyaltyPoints[]>;

@Injectable({ providedIn: 'root' })
export class LoyaltyPointsService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/loyalty-points');

  create(loyaltyPoints: NewLoyaltyPoints): Observable<EntityResponseType> {
    return this.http.post<ILoyaltyPoints>(this.resourceUrl, loyaltyPoints, { observe: 'response' });
  }

  update(loyaltyPoints: ILoyaltyPoints): Observable<EntityResponseType> {
    return this.http.put<ILoyaltyPoints>(`${this.resourceUrl}/${this.getLoyaltyPointsIdentifier(loyaltyPoints)}`, loyaltyPoints, {
      observe: 'response',
    });
  }

  partialUpdate(loyaltyPoints: PartialUpdateLoyaltyPoints): Observable<EntityResponseType> {
    return this.http.patch<ILoyaltyPoints>(`${this.resourceUrl}/${this.getLoyaltyPointsIdentifier(loyaltyPoints)}`, loyaltyPoints, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILoyaltyPoints>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILoyaltyPoints[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLoyaltyPointsIdentifier(loyaltyPoints: Pick<ILoyaltyPoints, 'id'>): number {
    return loyaltyPoints.id;
  }

  compareLoyaltyPoints(o1: Pick<ILoyaltyPoints, 'id'> | null, o2: Pick<ILoyaltyPoints, 'id'> | null): boolean {
    return o1 && o2 ? this.getLoyaltyPointsIdentifier(o1) === this.getLoyaltyPointsIdentifier(o2) : o1 === o2;
  }

  addLoyaltyPointsToCollectionIfMissing<Type extends Pick<ILoyaltyPoints, 'id'>>(
    loyaltyPointsCollection: Type[],
    ...loyaltyPointsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const loyaltyPoints: Type[] = loyaltyPointsToCheck.filter(isPresent);
    if (loyaltyPoints.length > 0) {
      const loyaltyPointsCollectionIdentifiers = loyaltyPointsCollection.map(loyaltyPointsItem =>
        this.getLoyaltyPointsIdentifier(loyaltyPointsItem),
      );
      const loyaltyPointsToAdd = loyaltyPoints.filter(loyaltyPointsItem => {
        const loyaltyPointsIdentifier = this.getLoyaltyPointsIdentifier(loyaltyPointsItem);
        if (loyaltyPointsCollectionIdentifiers.includes(loyaltyPointsIdentifier)) {
          return false;
        }
        loyaltyPointsCollectionIdentifiers.push(loyaltyPointsIdentifier);
        return true;
      });
      return [...loyaltyPointsToAdd, ...loyaltyPointsCollection];
    }
    return loyaltyPointsCollection;
  }
}
