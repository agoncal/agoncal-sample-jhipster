import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from './contact.service';
import { ILanguage } from 'app/shared/model/language.model';
import { LanguageService } from 'app/entities/language';

@Component({
    selector: 'jac-contact-update',
    templateUrl: './contact-update.component.html'
})
export class ContactUpdateComponent implements OnInit {
    contact: IContact;
    isSaving: boolean;

    languages: ILanguage[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private contactService: ContactService,
        private languageService: LanguageService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ contact }) => {
            this.contact = contact;
        });
        this.languageService.queryMinimalView().subscribe(
            (res: HttpResponse<ILanguage[]>) => {
                this.languages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.contact.id !== undefined) {
            this.subscribeToSaveResponse(this.contactService.update(this.contact));
        } else {
            this.subscribeToSaveResponse(this.contactService.create(this.contact));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IContact>>) {
        result.subscribe((res: HttpResponse<IContact>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackLanguageById(index: number, item: ILanguage) {
        return item.id;
    }
}
