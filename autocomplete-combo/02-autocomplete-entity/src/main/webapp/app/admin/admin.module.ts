import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { AutocompleteentitySharedModule } from 'app/shared';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import {
    adminState,
    AuditsComponent,
    UserMgmtComponent,
    UserMgmtDetailComponent,
    UserMgmtUpdateComponent,
    UserMgmtDeleteDialogComponent,
    LogsComponent,
    AueMetricsMonitoringModalComponent,
    AueMetricsMonitoringComponent,
    AueHealthModalComponent,
    AueHealthCheckComponent,
    AueConfigurationComponent,
    AueDocsComponent
} from './';

@NgModule({
    imports: [
        AutocompleteentitySharedModule,
        RouterModule.forChild(adminState)
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    ],
    declarations: [
        AuditsComponent,
        UserMgmtComponent,
        UserMgmtDetailComponent,
        UserMgmtUpdateComponent,
        UserMgmtDeleteDialogComponent,
        LogsComponent,
        AueConfigurationComponent,
        AueHealthCheckComponent,
        AueHealthModalComponent,
        AueDocsComponent,
        AueMetricsMonitoringComponent,
        AueMetricsMonitoringModalComponent
    ],
    entryComponents: [UserMgmtDeleteDialogComponent, AueHealthModalComponent, AueMetricsMonitoringModalComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutocompleteentityAdminModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
