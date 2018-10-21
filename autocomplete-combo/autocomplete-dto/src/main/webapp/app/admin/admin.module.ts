import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { AutocompletedtoSharedModule } from 'app/shared';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import {
    adminState,
    AuditsComponent,
    UserMgmtComponent,
    UserMgmtDetailComponent,
    UserMgmtUpdateComponent,
    UserMgmtDeleteDialogComponent,
    LogsComponent,
    AudMetricsMonitoringModalComponent,
    AudMetricsMonitoringComponent,
    AudHealthModalComponent,
    AudHealthCheckComponent,
    AudConfigurationComponent,
    AudDocsComponent
} from './';

@NgModule({
    imports: [
        AutocompletedtoSharedModule,
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
        AudConfigurationComponent,
        AudHealthCheckComponent,
        AudHealthModalComponent,
        AudDocsComponent,
        AudMetricsMonitoringComponent,
        AudMetricsMonitoringModalComponent
    ],
    entryComponents: [UserMgmtDeleteDialogComponent, AudHealthModalComponent, AudMetricsMonitoringModalComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutocompletedtoAdminModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
