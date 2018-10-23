import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { Contact, IContact } from 'app/shared/model/contact.model';
import { ContactService } from './contact.service';
import { ILanguage, Language } from 'app/shared/model/language.model';
import { LanguageService } from 'app/entities/language';
import { map } from 'rxjs/operators';

@Component({
    selector: 'aud-contact-update',
    templateUrl: './contact-update.component.html'
})
export class ContactUpdateComponent implements OnInit {
    contact: IContact;
    isSaving: boolean;

    languages: ILanguage[];
    language: ILanguage;

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
            this.languageService.find(this.contact.languageId).pipe(map((language: HttpResponse<Language>) => this.language = language.body));
        });
    }

    searchLanguages($event) {
        this.languageService.query({'name.contains': $event.query}).subscribe(
            (res: HttpResponse<ILanguage[]>) => {
                this.languages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    captureLanguageId($event) {
        this.contact.languageId = $event.id;
        this.contact.languageName = $event.name;
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
