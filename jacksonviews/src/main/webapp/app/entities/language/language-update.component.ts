import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ILanguage } from 'app/shared/model/language.model';
import { LanguageService } from './language.service';

@Component({
    selector: 'jac-language-update',
    templateUrl: './language-update.component.html'
})
export class LanguageUpdateComponent implements OnInit {
    language: ILanguage;
    isSaving: boolean;

    constructor(private languageService: LanguageService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ language }) => {
            this.language = language;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.language.id !== undefined) {
            this.subscribeToSaveResponse(this.languageService.update(this.language));
        } else {
            this.subscribeToSaveResponse(this.languageService.create(this.language));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILanguage>>) {
        result.subscribe((res: HttpResponse<ILanguage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
