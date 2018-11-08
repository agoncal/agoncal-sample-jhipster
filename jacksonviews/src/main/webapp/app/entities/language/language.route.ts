import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Language } from 'app/shared/model/language.model';
import { LanguageService } from './language.service';
import { LanguageComponent } from './language.component';
import { LanguageDetailComponent } from './language-detail.component';
import { LanguageUpdateComponent } from './language-update.component';
import { LanguageDeletePopupComponent } from './language-delete-dialog.component';
import { ILanguage } from 'app/shared/model/language.model';

@Injectable({ providedIn: 'root' })
export class LanguageResolve implements Resolve<ILanguage> {
    constructor(private service: LanguageService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Language> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Language>) => response.ok),
                map((language: HttpResponse<Language>) => language.body)
            );
        }
        return of(new Language());
    }
}

export const languageRoute: Routes = [
    {
        path: 'language',
        component: LanguageComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jacksonviewsApp.language.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'language/:id/view',
        component: LanguageDetailComponent,
        resolve: {
            language: LanguageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jacksonviewsApp.language.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'language/new',
        component: LanguageUpdateComponent,
        resolve: {
            language: LanguageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jacksonviewsApp.language.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'language/:id/edit',
        component: LanguageUpdateComponent,
        resolve: {
            language: LanguageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jacksonviewsApp.language.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const languagePopupRoute: Routes = [
    {
        path: 'language/:id/delete',
        component: LanguageDeletePopupComponent,
        resolve: {
            language: LanguageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jacksonviewsApp.language.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
